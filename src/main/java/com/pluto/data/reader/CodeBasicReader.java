package com.pluto.data.reader;

import com.pluto.bean.CodeBasic;
import com.pluto.helper.CodeHelper;

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

    private final String dataPath;

    public CodeBasicReader(String dataPath) {
        this.dataPath = dataPath;
    }

    @Override
    public Map<String, CodeBasic> getDataAll() {
        if (codeBasicMap == null) {
            List<String> fileContents = CodeHelper.readFromFile(dataPath);
            codeBasicMap = fileContents.stream().map(this::transferCodeBasic).filter(this::filterCodeBasic).collect(Collectors.toMap(CodeBasic::getCode, code -> code));
        }
        return codeBasicMap;
    }

    @Override
    public Map<String, CodeBasic> getDataByCondition(String key) {
        if (codeBasicMap == null) {
            List<String> fileContents = CodeHelper.readFromFile(dataPath);
            codeBasicMap = fileContents.stream().map(this::transferCodeBasic).filter(this::filterCodeBasic).collect(Collectors.toMap(CodeBasic::getCode, code -> code));
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

    public CodeBasic transferCodeBasic(String content) {
        if (content == null || content.isEmpty()) {
            return null;
        }
        String[] array = content.split(",", -1);
        CodeBasic codeBasic = new CodeBasic();
        codeBasic.setCode(array[0]);
        codeBasic.setTradeStatus(array[1]);
        codeBasic.setCodeName(array[2]);
        codeBasic.setIpoDate(array[3]);
        codeBasic.setOutDate(array[4]);
        codeBasic.setType(array[5]);
        codeBasic.setStatus(array[6]);
        codeBasic.setIndustryUpdateDate(array[7]);
        codeBasic.setIndustry(array[8]);
        codeBasic.setIndustryClassification(array[9]);
        return codeBasic;
    }
}
