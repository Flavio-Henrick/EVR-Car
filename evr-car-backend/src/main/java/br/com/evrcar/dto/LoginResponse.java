package br.com.evrcar.dto;

public record LoginResponse(String token, String tokenType, long expiresInSeconds) {}
