package api

import (
	"CDNServer/ImgHandle"
	"CDNServer/StorageHandle"
	"fmt"
	"net/http"
	"path/filepath"
	"regexp"
	"strconv"

	"github.com/gin-gonic/gin"
)

func UploadFiles(ctx *gin.Context) {
	form, err := ctx.MultipartForm()
	if err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"msg": err})
		return
	}
	files := form.File["files"]
	fileNameReponse := make(map[string]string)
	for _, file := range files {
		filename := filepath.Base(file.Filename)
		if path, err := StorageHandle.CreateFileAutoFolder(filename, file); err != nil {
			ctx.JSON(http.StatusBadRequest, gin.H{"msg": err})
			return
		} else {
			fileNameReponse[filename] = path
		}
	}

	ctx.JSON(http.StatusOK, fileNameReponse)
}

func UploadFile(ctx *gin.Context) {
	file, err := ctx.FormFile("file")
	if err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"msg": err})
		return
	}

	filename := filepath.Base(file.Filename)
	if path, err := StorageHandle.CreateFileAutoFolder(filename, file); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"msg": err})
		return
	} else {
		ctx.JSON(http.StatusOK, gin.H{filename: path})
	}

}

func DownloadFile(ctx *gin.Context) {
	filename := ctx.Param("filename")

	re := regexp.MustCompile("(.+?)(\\.[^.]*$|$)")
	match := re.FindStringSubmatch(filename)
	ext := match[2]

	path := StorageHandle.FindFileinStorage(filename)
	if path == "" {
		ctx.JSON(404, gin.H{"msg": "file non exist"})

		return
	}

	w, _ := strconv.ParseUint(ctx.Query("width"), 10, 64)
	h, _ := strconv.ParseUint(ctx.Query("height"), 10, 64)

	width := uint(w)
	height := uint(h)

	if width == 0 && height == 0 {
		if IsAttachment(ext) {
			fmt.Println("test")
			ctx.FileAttachment(path, path)
		} else {
			ctx.File(path)
		}
		return
	} else {
		patht, err := ImgHandle.Resize(path, width, height)
		if err != nil {
			ctx.JSON(http.StatusBadRequest, gin.H{"msg": err})
		} else {
			ctx.File(patht)

		}
		return
	}
	ctx.JSON(404, gin.H{"msg": "file non exist"})
}

func IsAttachment(ext string) bool {
	switch ext {
	case ".png":
	case ".jpg":
	case ".jpeg":
		return false
	}
	return true

}
