package christmas.controller.handler;

import christmas.view.OutputView;

public abstract class RetryHandler<E> implements RetryHandlerController<E>{
    private final OutputView outputView = new OutputView();

    @Override
    public E process() {
        try {
            return doProcess();
        } catch (IllegalArgumentException exception) {
            outputView.errorMessage(exception.getMessage());
            return process();
        }
    }

    protected abstract E doProcess();
}
