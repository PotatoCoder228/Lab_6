package ru.potatocoder228.itmo.lab6;

import ru.potatocoder228.itmo.lab6.connection.AnswerMsg;
import ru.potatocoder228.itmo.lab6.data.Coordinates;
import ru.potatocoder228.itmo.lab6.data.Dragon;
import ru.potatocoder228.itmo.lab6.data.DragonCave;
import ru.potatocoder228.itmo.lab6.data.DragonType;
import ru.potatocoder228.itmo.lab6.exceptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class ClientConsole {
    private boolean scriptMode;
    private Scanner scanner;
    private LinkedList<Scanner> scanners = new LinkedList<>();
    private LinkedList<String> namesOfScripts = new LinkedList<>();
    private boolean recursive = false;

    public ClientConsole(boolean mode) {
        this.scriptMode = mode;
    }

    public AnswerMsg inputCommand() throws FileNotFoundException, RecursiveScriptExecuteException {
        System.out.print("Введите команду:");
        if (!scriptMode) {
            scanner = new Scanner(System.in);
        }
        String command = scanner.nextLine().toLowerCase();
        AnswerMsg msg = new AnswerMsg();
        String[] lines = command.split("\\s+", 2);
        if (lines.length == 2 && (lines[0].equals("filter_less_than_cave") || lines[0].equals("remove_by_id") || lines[0].equals("filter_greater_than_description"))) {
            msg.setMessage(command);
        } else if (lines.length == 1 && !lines[0].equals("add") && !lines[0].equals("add_if_max") && !lines[0].equals("remove_greater")) {
            msg.setMessage(command);
        } else if (lines.length == 2 && lines[0].equals("update")) {
            msg.setMessage(command);
            Dragon dragon = inputDragon();
            msg.setDragon(dragon);
        } else if (lines.length == 1) {
            Dragon dragon = inputDragon();
            msg.setDragon(dragon);
            msg.setMessage(command);
        } else if (lines.length == 2 && lines[0].equals("execute_script")) {
            if (!scriptMode) {
                scriptMode = true;
                this.scanner = new Scanner(new File(lines[1]));
                scanners.add(this.scanner);
                namesOfScripts.add(lines[1]);
            } else {
                for (String i : namesOfScripts) {
                    if (lines[1].equals(i)) {
                        recursive = true;
                    }
                }
                if (!recursive) {
                    this.scanner = new Scanner(new File(lines[1]));
                    scanners.add(this.scanner);
                    namesOfScripts.add(lines[1]);
                } else {
                    namesOfScripts.clear();
                    scanners.clear();
                    recursive = false;
                    throw new RecursiveScriptExecuteException();
                }
            }
            msg.setMessage(this.scanner.nextLine());
        }
        return msg;
    }

    public Dragon inputDragon() {
        Dragon dragon;
        String name = inputName();
        long x = inputX();
        Double y = inputY();
        int age = inputAge();
        Coordinates coordinates = new Coordinates(x, y);
        String description = inputDescription();
        Boolean speaking = inputSpeaking();
        DragonType type = inputType();
        DragonCave cave = inputCave();
        dragon = new Dragon(name, coordinates, age, description, speaking, type, cave);
        return dragon;
    }

    public String getConsole() {
        boolean checking = false;
        String Field = "";
        while (!checking) {
            try {
                ;
                Field = scanner.nextLine();
                checking = true;
            } catch (Exception e) {
                System.out.println("Некорректные данные, попробуйте ввести ещё раз:");
            }
        }
        return Field;
    }

    public String inputName() {
        if (scriptMode) {
            return scanner.nextLine();
        } else {
            System.out.print("Введите имя дракона:");
            return getConsole();
        }
    }

    public String inputDescription() {
        if (scriptMode) {
            return scanner.nextLine();
        } else {
            System.out.print("Введите описание дракона:");
            return getConsole();
        }
    }

    public long inputX() {
        long x;
        try {
            if (scriptMode) {
                x = Long.parseLong(scanner.nextLine());
                if (x > 436) {
                    throw new WrongCoordinatesException("Некорректное значение Ox.");
                }
            } else {
                System.out.print("Введите координату x(целое число до 436): ");
                x = Long.parseLong(getConsole());
                while (x > 436) {
                    System.out.print("Некорректный ввод, это поле - целое число до 436 включительно: ");
                    x = Long.parseLong(getConsole());
                }
            }
        } catch (NumberFormatException e) {
            System.out.print("Некорректное значение поля. Попробуйте ввести его ещё раз: ");
            x = inputX();
        }
        return x;
    }

    public Double inputY() {
        Double y;
        try {
            if (scriptMode) {
                y = Double.parseDouble(scanner.nextLine());
                if (y > 101) {
                    throw new WrongCoordinatesException("Некорректное значение Oy.");
                }
            } else {
                System.out.print("Введите координату y(число с плавающей точкой до 101, не null): ");
                y = Double.parseDouble(getConsole());
                while (y > 101) {
                    System.out.print("Некорректный ввод, это поле - число с плавающей ТОЧКОЙ до 101 включительно: ");
                    y = Double.parseDouble(getConsole());
                }
            }
        } catch (NumberFormatException e) {
            System.out.print("Некорректное значение поля. Попробуйте ввести его ещё раз: ");
            y = inputY();
        }
        return y;
    }

    public int inputAge() {
        int age;
        try {
            if (scriptMode) {
                try {
                    age = Integer.parseInt(getConsole());
                    if (age < 1) {
                        throw new WrongAgeException("Некорректное значение возраста.");
                    }
                } catch (NumberFormatException e) {
                    throw new WrongAgeException("Некорректное значение возраста.");
                }
            } else {
                System.out.print("Введите возраст(значение больше 0): ");
                age = Integer.parseInt(getConsole());
                while (age < 1) {
                    System.out.print("Некорректный ввод, это поле - целое число больше 0: ");
                    age = Integer.parseInt(getConsole());
                }
            }
        } catch (NumberFormatException e) {
            System.out.print("Некорректный ввод, это поле - целое число больше 0: ");
            age = Integer.parseInt(getConsole());
        }
        return age;
    }

    public Boolean inputSpeaking() {
        boolean speak;
        try {
            if (scriptMode) {
                try {
                    speak = Boolean.parseBoolean(getConsole());
                } catch (NumberFormatException e) {
                    throw new WrongSpeakingException("Некорректное значение поля speaking.");
                }
            } else {
                System.out.print("Введите speaking: ");
                speak = Boolean.parseBoolean(getConsole());
            }
        } catch (NumberFormatException e) {
            System.out.print("Некорректный ввод, это поле - целое число больше 0: ");
            speak = Boolean.parseBoolean(getConsole());
        }
        return speak;
    }

    public DragonType inputType() {
        DragonType type;
        if (scriptMode) {
            try {
                type = DragonType.valueOf(getConsole().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new WrongTypeException("Некорректный тип объекта.");
            }
        } else {
            try {
                System.out.print("Введите тип элемента(WATER,FIRE,AIR или UNDERGROUND): ");
                type = DragonType.valueOf(getConsole().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректный тип дракона.");
                type = inputType();
            }
        }
        return type;
    }

    public DragonCave inputCave() {
        DragonCave cave;
        if (scriptMode) {
            try {
                cave = new DragonCave(Float.parseFloat(getConsole()));
            } catch (NumberFormatException e) {
                throw new WrongCaveException();
            }
        } else {
            try {
                System.out.print("Введите размер пещеры(число с плавающей точкой): ");
                cave = new DragonCave(Float.parseFloat(getConsole()));
            } catch (NumberFormatException e) {
                System.out.println("Некорректное поле cave.");
                cave = inputCave();
            }
        }
        return cave;
    }
}
