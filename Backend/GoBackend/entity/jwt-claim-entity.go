package entity

import "github.com/dgrijalva/jwt-go"

type JwtClaimEntity struct {
	ID   string `json:"id"`
	User string `json:"user"`
	//ExpiresAt int64  `json:"exp,omitempty"`
	//Issuer    string `json:"iss,omitempty"`
	jwt.StandardClaims
}
