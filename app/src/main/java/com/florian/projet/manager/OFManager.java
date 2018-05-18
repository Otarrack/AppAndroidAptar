package com.florian.projet.manager;

import com.florian.projet.quarantaine.OF;

import java.util.ArrayList;
import java.util.List;

public class OFManager {
    private static OFManager instance;
    private List<OF> listOF;

    private OFManager() {
        listOF = new ArrayList<>();
    }

    public static OFManager getInstance() {
        if(instance == null) {
            instance = new OFManager();
        }
        return instance;
    }

    public List<OF> getAllOf() {
        return listOF;
    }

    public OF getOFByNum(String numOF) {
        List<OF> ofList = getAllOf();
        OF resultOF = null;

        for (OF of: ofList) {
            if (of.getNumOF().equals(numOF)) {
                resultOF = of;
            }
        }

        return resultOF;
    }

    public void addOf(OF of) {
        listOF.add(of);
    }
}
