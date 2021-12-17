package com.vijay.mystations.core.network;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Result<T> {

    @NonNull
    public final Status status;
    @Nullable public final T data;
    @Nullable public final String message;

    private Result(@NonNull Status status, @Nullable T data,
                     @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Result<T> success(@NonNull T data) {
        return new Result<>(Status.SUCCESS, data, null);
    }

    public static <T> Result<T> error(String msg, @Nullable T data) {
        return new Result<>(Status.ERROR, data, msg);
    }

    public static <T> Result<T> loading(@Nullable T data) {
        return new Result<>(Status.LOADING, data, null);
    }


    public enum Status { SUCCESS, ERROR, LOADING }
}
