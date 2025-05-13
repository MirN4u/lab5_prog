package commands;

import utility.ExecutionResponse;

public interface Executable {
    ExecutionResponse apply(String arguments);
}
