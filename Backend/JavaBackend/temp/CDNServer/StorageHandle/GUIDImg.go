package StorageHandle

import (
	"crypto/md5"
	"fmt"
	"io"
	"time"
)

func GenerationGUID() string {
	codeHash := md5.New()
	io.WriteString(codeHash, time.Now().String())
	return fmt.Sprintf("%x", codeHash.Sum(nil))
}
