package ctp.command;

import org.iplass.mtp.command.Command;
import org.iplass.mtp.command.RequestContext;

public class RadikoScheduleToProgramCommand implements Command {
    @Override
    public String execute(RequestContext request) {
        String paramX = request.getParam("x");

        boolean success = true;

        if (success) {
            return "SUCCESS";
        } else {
            return "FAIL";
        }
    }
}