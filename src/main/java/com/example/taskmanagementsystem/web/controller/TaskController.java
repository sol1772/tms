package com.example.taskmanagementsystem.web.controller;

import com.example.taskmanagementsystem.domain.*;
import com.example.taskmanagementsystem.domain.dto.PageRequestDto;
import com.example.taskmanagementsystem.service.TaskService;
import com.example.taskmanagementsystem.service.UserService;
import com.example.taskmanagementsystem.service.validation.TaskValidator;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/task")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private static final String ERROR = "error";
    private static final String EXC_MSG = "exceptionMessage";
    private static final String TASK_ADD = "task-add";
    private static final String TASK = "task";
    private static final String TASKS = "tasks";
    private final TaskService taskService;
    private final UserService userService;
    private final TaskValidator taskValidator;

    public TaskController(TaskService taskService, UserService userService, TaskValidator taskValidator) {
        this.userService = userService;
        this.taskService = taskService;
        this.taskValidator = taskValidator;
    }

    @GetMapping("/{id}")
    public String viewTask(@PathVariable Integer id, Model model) {
        Task task = taskService.getTaskById(id);
        if (!Objects.isNull(task)) {
            model.addAttribute(TASK, task);
            return TASK;
        } else {
            model.addAttribute(EXC_MSG, "Задача с id " + id + " не найдена");
            return ERROR;
        }
    }

    @GetMapping("/add")
    public String viewTaskAdd(Model model) {
        model.addAttribute("priorities", Arrays.asList(Priority.values()));
        model.addAttribute("users", userService.getAllUsers());
        return TASK_ADD;
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task, BindingResult result, @RequestParam Map<String, String> p,
                          HttpSession session, Model model) {
        String option = p.get("submit");
        if ("Cancel".equals(option)) {
            return "redirect:/";
        }
        User user = (User) session.getAttribute("user");
        task.setAuthor(user);
        task.setStatus(Status.PENDING);
        if (!validateFields(task)) {
            model.addAttribute(ERROR, "Не заполнены обязательные поля");
            return TASK_ADD;
        }
        if (result.hasErrors()) {
            model.addAllAttributes(p);
            model.addAttribute(ERROR, result.getAllErrors().get(0).getDefaultMessage());
            return TASK_ADD;
        }
        if ("Register".equals(option)) {
            try {
                Task dbTask = taskService.createTask(task);
                model.addAttribute("user", dbTask);
                return "redirect:/task/" + dbTask.getId();
            } catch (Exception e) {
                model.addAttribute(ERROR, e.getMessage());
                return TASK_ADD;
            }
        }
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String viewTaskEdit(@PathVariable Integer id, Model model) {
        Task task = taskService.getTaskById(id);
        if (!Objects.isNull(task)) {
            model.addAttribute(TASK, task);
            model.addAttribute("statuses", Arrays.asList(Status.values()));
            model.addAttribute("priorities", Arrays.asList(Priority.values()));
            model.addAttribute("users", userService.getAllUsers());
            return "task-edit";
        } else {
            model.addAttribute(EXC_MSG, "Task with id " + id + " not found");
            return ERROR;
        }
    }

    @PostMapping("/{id}/edit")
    public String editTask(@ModelAttribute Task task, BindingResult result, @RequestParam Map<String, String> p,
                           @RequestParam("submit") String option, HttpSession session, Model model) {
        taskValidator.validate(task, result);
        if (result.hasErrors()) {
            model.addAttribute(TASK, taskService.getTaskById(task.getId()));
            model.addAttribute(ERROR, result.getFieldErrors().get(0).getDefaultMessage());
            return "task";
        }
        if (option.equals("Save")) {
            Task dbTask = taskService.editTask(task);
            session.setAttribute(TASK, dbTask);
            model.addAttribute(TASK, dbTask);
            logger.info("Saved task {}", dbTask);
            return "redirect:/task/" + dbTask.getId();
        } else if (option.equals("Cancel")) {
            model.addAttribute(TASK, task);
            return "redirect:/task/" + task.getId();
        }
        return "task-edit";
    }

    @GetMapping("/{id}/comment/add")
    public String viewCommentAdd(@PathVariable Integer id, Model model) {
        Task task = taskService.getTaskById(id);
        if (!Objects.isNull(task)) {
            model.addAttribute(TASK, task);
            return "comment";
        } else {
            model.addAttribute(EXC_MSG, "Task with id " + id + " not found");
            return ERROR;
        }
    }

    @PostMapping("/{id}/comment/add")
    public String addComment(@PathVariable Integer id, @RequestParam Map<String, String> p,
                             HttpSession session, Model model) {
        String option = p.get("submit");
        if ("Send".equals(option)) {
            User user = (User) session.getAttribute("user");
            try {
                Task task = taskService.getTaskById(id);
                if (!Objects.isNull(task)) {
                    String comment = p.get("comment");
                    if (!comment.isEmpty()) {
                        List<Comment> comments = task.getComments();
                        comments.add(new Comment(comment, user, task));
                        Task dbTask = taskService.editTask(task);
                        model.addAttribute(TASK, task);
                        return "redirect:/task/" + dbTask.getId();
                    } else {
                        model.addAttribute("task", task);
                        model.addAttribute("report", "Введите комментарий");
                        return "comment";
                    }
                } else {
                    model.addAttribute(EXC_MSG, "Задача с id " + id + " не найдена");
                    return ERROR;
                }
            } catch (Exception e) {
                model.addAttribute("report", e.getMessage());
                return "comment";
            }
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/{id}/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteTask(@ModelAttribute Task task, BindingResult result, @PathVariable Integer id,
                             HttpSession session, Model model) {
        taskValidator.validateDeletion(task, result);
        if (result.hasErrors()) {
            List<Task> tasks = taskService.getAllTasks();
            model.addAttribute(TASKS, tasks);
            model.addAttribute("message", result.getFieldErrors().get(0).getDefaultMessage());
            return TASKS;
        }
        Task dbTask = taskService.getTaskById(id);
        if (!Objects.isNull(task)) {
            boolean taskDeleted = taskService.deleteTask(id);
            List<Task> tasks = taskService.getAllTasks();
            model.addAttribute(TASKS, tasks);
            if (taskDeleted) {
                model.addAttribute("message", "Задача № " + dbTask.getId() + " удалена");
                if (logger.isInfoEnabled()) {
                    logger.info("Удалена задача № {}", task.getId());
                }
            } else {
                model.addAttribute(ERROR, "Ошибка удаления задачи!");
                if (logger.isInfoEnabled()) {
                    logger.info("Ошибка удаления задачи № {}", task.getId());
                }
            }
        }
        return TASKS;
    }

    @GetMapping("")
    public String viewPageTasks(@RequestParam(name = "page", required = false) Integer page,
                                @RequestParam Map<String, String> p, Model model) {
        Page<Task> pageTasks;
        Integer currentPage = page == null ? 0 : page - 1;
        PageRequestDto dto = new PageRequestDto();
        dto.setPageNumber(currentPage);

        String filter = p.get("filter");
        String userId = p.get("user");
        if (!Objects.isNull(filter) && !Objects.isNull(userId)) {
            User user = userService.getUserById(Integer.parseInt(userId));
            model.addAttribute("filter", filter);
            model.addAttribute("user", user);
            pageTasks = filter.equals("author") ? taskService.getPageTasksByAuthor(dto, user) :
                    taskService.getPageTasksByPerformer(dto, user);
        } else {
            pageTasks = taskService.getPageAllTasks(dto);
        }
        int taskPages = pageTasks.getTotalPages();
        model.addAttribute("page", page == null ? 1 : page);
        model.addAttribute("taskPages", taskPages);
        model.addAttribute("taskTotal", pageTasks.getTotalElements());
        model.addAttribute("users", userService.getAllUsers());
        if (page != null && page != 1 && page > taskPages) {
            model.addAttribute(ERROR, "Страница " + page + " превышает максимальное количество страниц " + taskPages);
        }
        return TASKS;
    }

    @GetMapping("/tasks")
    @ResponseBody
    public List<Task> getPageTasks(@RequestParam(name = "page", required = false) Integer page,
                                   @RequestParam(name = "filter", required = false) String filter,
                                   @RequestParam(name = "user", required = false) String userId) {
        List<Task> tasks = new ArrayList<>();
        Integer currentPage = page == null ? 0 : page - 1;
        PageRequestDto dto = new PageRequestDto();
        dto.setPageNumber(currentPage);
        if (userId == null) {
            tasks = taskService.getPageAllTasks(dto).getContent();
        } else if (filter != null) {
            User user = userService.getUserById(Integer.parseInt(userId));
            if (filter.equals("author")) {
                tasks = taskService.getPageTasksByAuthor(dto, user).getContent();
            } else {
                tasks = taskService.getPageTasksByPerformer(dto, user).getContent();
            }
        }
        return tasks;
    }

    public boolean validateFields(Task task) {
        return !task.getTitle().isEmpty() && !Objects.isNull(task.getAuthor());
    }
}
