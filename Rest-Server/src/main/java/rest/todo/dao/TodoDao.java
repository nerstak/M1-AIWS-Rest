package rest.todo.dao;

import java.util.HashMap;
import java.util.Map;

import rest.todo.model.Todo;


public enum TodoDao {
    instance;

    private Map<String, Todo> contentProvider = new HashMap<>();

    private TodoDao() {

        Todo todo = new Todo("1", "Prendre un bon petit dej");
        todo.setDescription("il est important de commencer la journée par un repas équilibré");
        contentProvider.put("1", todo);
        todo = new Todo("2", "Faire 10000 pas");
        todo.setDescription("afin de garder une bonne santé");
        contentProvider.put("2", todo);

    }

    public Map<String, Todo> getModel() {
        return contentProvider;
    }

}