package kaa.nurlibaydev.paymentterminaltesttask.presentation.utils;

public abstract class ResultData<T> {

    private ResultData() { }

    // --- Success ---
    public static final class Success<T> extends ResultData<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    // --- Error ---
    public static final class Error<T> extends ResultData<T> {
        private final Throwable exception;

        public Error(Throwable exception) {
            this.exception = exception;
        }

        public Throwable getException() {
            return exception;
        }
    }
}
