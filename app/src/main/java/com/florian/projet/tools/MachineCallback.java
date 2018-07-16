package com.florian.projet.tools;

import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.bdd.relation.ArticleWithData;

import java.util.List;

public interface MachineCallback {
    void onSuccess(List<Machine> machineList);
    void onFailed(Exception e);
}
