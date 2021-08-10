package dev.calculator.service.operations;

import dev.calculator.model.ServiceEntry;
import dev.calculator.model.ServiceType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Component
public class FreeFormOperation implements Operation {

    @Override
    public Double execute(ServiceEntry serviceEntry) {
        String freeForm = serviceEntry.getFreeForm();
        if (!StringUtils.hasText(freeForm)) {
            return 0.0;
        }

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        try {
            return Double.valueOf(engine.eval(freeForm).toString());
        } catch (ScriptException e) {
            return 0.0;
        }
    }

    @Override
    public ServiceType getServiceType() {
        return ServiceType.FREE_FORM;
    }
}
