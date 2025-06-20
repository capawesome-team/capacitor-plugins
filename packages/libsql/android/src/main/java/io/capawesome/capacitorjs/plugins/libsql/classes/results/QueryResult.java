package io.capawesome.capacitorjs.plugins.libsql.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.JSArray;
import io.capawesome.capacitorjs.plugins.libsql.interfaces.Result;
import tech.turso.libsql.Rows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QueryResult implements Result {

    @NonNull
    private final List<List<Object>> rows;

    public QueryResult(@NonNull Rows rows) {
        this.rows = convertRowsToList(rows);
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray rowsResult = new JSArray();
        for (List<Object> row : rows) {
            JSArray rowResult = new JSArray();
            for (Object value : row) {
                rowResult.put(value);
            }
            rowsResult.put(rowResult);
        }

        JSObject result = new JSObject();
        result.put("rows", rowsResult);
        return result;
    }

    private List<List<Object>> convertRowsToList(@NonNull Rows rows) {
        List<List<Object>> rowsResult = new ArrayList<>();
        Iterator<List<Object>> rowsIterator = rows.iterator();
        while (rowsIterator.hasNext()) {
            rowsResult.add(rowsIterator.next());
        }
        return rowsResult;
    }
}