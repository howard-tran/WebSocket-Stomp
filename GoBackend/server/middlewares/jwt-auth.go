package middlewares

import (
	"GoBackend/service"
	"net/http"

	"github.com/dgrijalva/jwt-go"
	"github.com/gin-gonic/gin"
)

func AuthorizeJWT() gin.HandlerFunc {
	return func(ctx *gin.Context) {
		tokenString := ctx.GetHeader("token")
		token, err := service.NewJwtService().ValidateToken(tokenString)
		if err == nil {
			if token.Valid {
				claims := token.Claims.(jwt.MapClaims)
				ctx.Set("ClaimJwt", claims)
			}
		} else {
			ctx.AbortWithStatus(http.StatusUnauthorized)
		}
	}
}
