package ru.potatocoder228.itmo.lab6.commands;


import ru.potatocoder228.itmo.lab6.data.CollectionManager;

import java.util.Map;

/**
 * Команда, выводящая информацию о командах
 */

public class Help implements Command {
    protected String nameOfCommand;
    protected String description;
    protected String arg;
    /**
     * Конструктор, задающий параметры для создания объекта
     *
     * @param info "словарь", возвращающий описание команды по ключу
     * @param map  "словарь", возвращающий объекты классов, наследующихся от Command
     */

    public Help(Map<String, String> info, Map<String, Command> map) {
        nameOfCommand = "help";
        description = "вывод справки по доступным командам.";
        info.put(nameOfCommand, description);//кладём описание
        map.put(nameOfCommand, this);
    }


    @Override
    public String execute(CollectionManager collectionManager) {
        //
        return nameOfCommand;
    }
    public void setArg(String arg){
        this.arg = arg;
    }
}
