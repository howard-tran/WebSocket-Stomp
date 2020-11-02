package service

import (
	"GoBackend/entity"
	"GoBackend/utility"
	"errors"
	"fmt"
	"time"

	"github.com/dgrijalva/jwt-go"
)

type JwtService interface {
	GenerationToken(id string, username string) string
	ValidateToken(string) (*jwt.Token, error)
}

type jwtService struct {
	secret string
	issuer string
}

func NewJwtService() JwtService {
	var jwtConfig utility.JwtClaimStruct
	jwtConfig = utility.GetConfigServerbyKey(utility.Auth).(utility.JwtClaimStruct)
	///fmt.Errorf(jwtConfig.Secretkey)
	return &jwtService{
		secret: jwtConfig.Secretkey,
		issuer: jwtConfig.Issuer,
	}
}

func (c *jwtService) ValidateToken(token string) (*jwt.Token, error) {
	return jwt.Parse(token, func(token *jwt.Token) (interface{}, error) {
		if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
			return nil, errors.New("No Signing Method")
		}
		return []byte(c.secret), nil
	})
}

func (c *jwtService) GenerationToken(id string, username string) string {
	claims := &entity.JwtClaimEntity{
		id,
		username,
		jwt.StandardClaims{
			ExpiresAt: time.Now().Add(time.Hour * 72).Unix(),
			Issuer:    c.issuer,
			IssuedAt:  time.Now().Unix(),
		},
	}
	//fmt.Errorf(claims)
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	tokenstring, er := token.SignedString([]byte(c.secret))
	if er != nil {
		fmt.Printf("[ERROR] Generation JWT Token for %s failre: %s", id, er)
	}
	return tokenstring
}
