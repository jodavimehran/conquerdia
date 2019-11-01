package ca.concordia.encs.conquerdia.model;


import ca.concordia.encs.conquerdia.util.Observable;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandResultModel extends Observable {
    private static CommandResultModel instance;
    private final List<String> resultList = new ArrayList<>();

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

    /**
     * Clear the model
     */
    public void clear() {
        resultList.clear();
    }

    /**
     * @param resultList The list of result to be added to this model
     */
    public void addResultList(List<String> resultList) {
        if (resultList != null && !resultList.isEmpty()) {
            this.resultList.addAll(resultList.stream().filter(StringUtils::isNotBlank).collect(Collectors.toSet()));
            setChanged();
            notifyObservers(this);
        }
    }

    /**
     * @param result The result to be added to this model
     */
    public void addResult(String result) {
        if (!StringUtils.isBlank(result)) {
            this.resultList.add(result);
            setChanged();
            notifyObservers(this);
        }
    }

    /**
     * @return result list
     */
    public List<String> getResultList() {
        return resultList;
    }
}
