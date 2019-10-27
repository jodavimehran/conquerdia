package ca.concordia.encs.conquerdia.model;


import ca.concordia.encs.conquerdia.util.Observable;

public class CommandResultModel extends Observable {
    private static CommandResultModel instance;
    private String result;

    private CommandResultModel() {
    }

    public static CommandResultModel getInstance() {
        if (instance == null) {
            synchronized (CommandResultModel.class) {
                if (instance == null) {
                    instance = new CommandResultModel();
                }
            }
        }
        return instance;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
        setChanged();
        notifyObservers(this);
    }
}
