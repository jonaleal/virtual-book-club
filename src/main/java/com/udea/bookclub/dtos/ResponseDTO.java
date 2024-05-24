package com.udea.bookclub.dtos;

public record ResponseDTO<T>(String message, T data) {
}
