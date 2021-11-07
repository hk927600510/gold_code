package com.pluto.data.reader;

import com.pluto.bean.CodeBasic;
import com.pluto.helper.SqliteDAOHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/2
 */
public class CodeBasicReader implements Reader<Map<String, CodeBasic>> {

    private Map<String, CodeBasic> codeBasicMap;

    @Override
    public Map<String, CodeBasic> getDataAll() {
        if (codeBasicMap == null) {
            List<CodeBasic> result = SqliteDAOHelper.queryCodeBasic("");
            codeBasicMap = result.stream().filter(this::filterCodeBasic).collect(Collectors.toMap(CodeBasic::getCode, code -> code));
        }
        return codeBasicMap;
    }

    @Override
    public Map<String, CodeBasic> getDataByCondition(String key) {
        if (codeBasicMap == null) {
            List<CodeBasic> result = SqliteDAOHelper.queryCodeBasic("");
            codeBasicMap = result.stream().filter(this::filterCodeBasic).collect(Collectors.toMap(CodeBasic::getCode, code -> code));
        }
        Map<String, CodeBasic> map = new HashMap<>();
        map.put(key, codeBasicMap.get(key));
        return map;
    }

    private boolean filterCodeBasic(CodeBasic codeBasic) {
        if (codeBasic == null) {
            return false;
        }
        if (codeBasic.getType() != null && !"1" .equals(codeBasic.getType())) {
            return false;
        }
        if (codeBasic.getStatus() != null && "0" .equals(codeBasic.getStatus())) {
            return false;
        }
        return true;
    }
}
