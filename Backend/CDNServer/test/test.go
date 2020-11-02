



// package main

// import (
// 	"errors"
// 	"fmt"
// 	"image"
// 	"image/jpeg"
// 	"image/png"
// 	"log"
// 	"os"
// 	"path/filepath"
// 	"regexp"

// 	"github.com/nfnt/resize"

// 	"github.com/gin-gonic/gin"
// )

// const path = "test/haha/tes.cdt.jpg"

// func main() {
// 	// open "test.jpg"
// 	app := gin.New()
// 	file, err := os.Open(path)
// 	if err != nil {
// 		log.Fatal(err)
// 	}

// 	dir := "/"

// 	re := regexp.MustCompile(`^(.+)\/([^\/]+)$`)
// 	lendir := re.FindStringSubmatch(path)
// 	fmt.Println(lendir)
// 	if len(lendir) == 3 {
// 		dir = lendir[1]
// 	}

// 	re = regexp.MustCompile("(.+?)(\\.[^.]*$|$)")
// 	match := re.FindStringSubmatch(file.Name())
// 	fmt.Println(match[1])

// 	// decode jpeg into image.Image

// 	img, err := ImgDecoder(file, match[2]) //png.Decode(file)
// 	if err != nil {
// 		log.Fatal(err)
// 	}
// 	file.Close()

// 	// resize to width 1000 using Lanczos resampling
// 	// and preserve aspect ratio
// 	m := resize.Resize(0, 50, img, resize.Lanczos3)

// 	info := fmt.Sprintf("%dx%d", m.Bounds().Size().X, m.Bounds().Size().Y)
// 	out, err := os.Create(filepath.Join("hshs", fmt.Sprintf("%s_%s%s", match[1], info, match[2])))
// 	//out, err := os.Create("test_resized.jpg")
// 	if err != nil {
// 		log.Fatal(err)
// 	}
// 	defer out.Close()

// 	// write new image to file
// 	//png.en
// 	ImgEncoder(out, match[2], m)
// 	fmt.Println(dir)

// 	app.Run(":8080")
// }

// func ImgEncoder(file *os.File, ext string, img image.Image) error {
// 	fmt.Println(ext)
// 	switch ext {
// 	case ".png":
// 		return png.Encode(file, img)
// 	case ".jpg":
// 		return jpeg.Encode(file, img, nil)
// 	case ".jpeg":
// 		return jpeg.Encode(file, img, nil)
// 	}
// 	return errors.New("Format file unsuport")
// }

// func ImgDecoder(file *os.File, ext string) (image.Image, error) {
// 	fmt.Println(ext)
// 	switch ext {
// 	case ".png":
// 		return png.Decode(file)
// 	case ".jpg":
// 		return jpeg.Decode(file)
// 	case ".jpeg":
// 		return jpeg.Decode(file)
// 	}
// 	return nil, errors.New("Format file unsuport")
// }
