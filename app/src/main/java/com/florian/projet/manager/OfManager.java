package com.florian.projet.manager;

import com.florian.projet.model.OF;

import java.util.ArrayList;
import java.util.List;

public class OfManager {
    private static OfManager instance;
    private List<OF> listOF;

    private OfManager() {
        listOF = new ArrayList<>();
    }

    public static OfManager getInstance() {
        if(instance == null) {
            instance = new OfManager();
        }
        return instance;
    }

    public List<OF> getAllOf() {
        return listOF;
    }

    public void addOf(OF of) {
        listOF.add(of);
    }
}
