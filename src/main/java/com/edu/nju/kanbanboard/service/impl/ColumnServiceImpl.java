package com.edu.nju.kanbanboard.service.impl;

import com.edu.nju.kanbanboard.model.domain.KBColumn;
import com.edu.nju.kanbanboard.repository.ColumnRepository;
import com.edu.nju.kanbanboard.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColumnServiceImpl implements ColumnService {
    @Autowired
    private ColumnRepository columnRepository;

    @Override
    public void create(KBColumn column) {
        column.setColumnFlag(1);
        column.setCardsFlag(1);
        columnRepository.save(column);
    }

    @Override
    public void update(KBColumn column) {
        columnRepository.save(column);
    }

    @Override
    public KBColumn getById(Long columnId) {
        return columnRepository.getOne(columnId);
    }

    @Override
    public Optional<KBColumn> delete(Long columnId) {
        final Optional<KBColumn> column = columnRepository.findById(columnId);
        columnRepository.delete(column.orElse(null));
        return column;
    }

    @Override
    public List<KBColumn> resortColumn(List<KBColumn> columns, int order) {
        int flag = order<0 ? -1:1;
        order = Math.abs(order);

        for(KBColumn column:columns){
            if(column.getColumnOrder()>= order){
                column.setColumnOrder(column.getColumnOrder()+flag);
            }
        }

        return columns;
    }

    @Override
    public boolean getColumnAuthority(Long ColumnId) {
        Optional<KBColumn> column = columnRepository.findById(ColumnId);
        if(column.isPresent()){
            if(column.get().getColumnFlag()>0 && column.get().getCardsFlag()>0){
                column.get().setCardsFlag(0);
                column.get().setColumnFlag(0);
                columnRepository.save(column.get());
                return true;
            }
        }
        return false;
    }

    @Override
    public void relaseColumnAuthority(Long ColumnId) {
        Optional<KBColumn> column = columnRepository.findById(ColumnId);
        if(column.isPresent()){
            column.get().setColumnFlag(0);
            column.get().setCardsFlag(0);
            columnRepository.save(column.get());
        }
    }


}
