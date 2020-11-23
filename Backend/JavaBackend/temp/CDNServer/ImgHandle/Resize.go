package ImgHandle

import (
	"CDNServer/StorageHandle"
	"errors"
	"fmt"
	"image"
	"image/jpeg"
	"image/png"
	"os"
	"regexp"

	"github.com/nfnt/resize"
)

func Resize(path string, width uint, height uint) (string, error) {
	dir := "/"

	//Get dir file
	re := regexp.MustCompile(`^(.+)\/([^\/]+)$`)
	lendir := re.FindStringSubmatch(path)
	//fmt.Println(lendir)
	if len(lendir) == 3 {
		dir = lendir[1]
	}

	//Open file
	file, err := os.Open(path)
	if err != nil {
		fmt.Println(err)
	}

	//Split name, ext
	re = regexp.MustCompile("(.+?)(\\.[^.]*$|$)")
	match := re.FindStringSubmatch(lendir[2])
	//fmt.Println(match[1])

	// decode ext into image.Image
	img, err := ImgDecoder(file, match[2])
	if err != nil {
		fmt.Println(err)
		return "", err
	}
	file.Close()

	//resize file
	m := resize.Resize(width, height, img, resize.Lanczos3)
	info := fmt.Sprintf("%dx%d", m.Bounds().Size().X, m.Bounds().Size().Y)
	namenew := fmt.Sprintf("%s_%s%s", match[1], info, match[2])

	//Check exist file

	//fmt.Println(namenew)
	if pathF := StorageHandle.FindFileinStorage(namenew); pathF != "" {
		return pathF, nil
	}

	//fmt.Println(dir)
	//fmt.Println("KAKAKAKAK")
	//Create file together folder
	out, err := StorageHandle.CreateFileEmptytoFolder(namenew, dir) //os.Create(fmt.Sprintf("%s_%s%s", match[1], info, match[2]))
	if err != nil {
		fmt.Println(err)
		return "", err
	}
	defer out.Close()

	// write new image to file
	ImgEncoder(out, match[2], m)

	//fmt.Println(path + "mjhmhj")
	//fmt.Println(dir)
	return dir + "/" + namenew, nil
}

func ImgEncoder(file *os.File, ext string, img image.Image) error {
	//fmt.Println(ext)
	switch ext {
	case ".png":
		return png.Encode(file, img)
	case ".jpg":
		return jpeg.Encode(file, img, nil)
	case ".jpeg":
		return jpeg.Encode(file, img, nil)
	}
	return errors.New("Format file unsuport")
}

func ImgDecoder(file *os.File, ext string) (image.Image, error) {
	//fmt.Println(ext)
	switch ext {
	case ".png":
		return png.Decode(file)
	case ".jpg":
		return jpeg.Decode(file)
	case ".jpeg":
		return jpeg.Decode(file)
	}
	return nil, errors.New("Format file unsuport")
}
