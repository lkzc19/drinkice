package controller

import (
	"encoding/json"
	"fmt"
	"strings"

	"github.com/clbanning/mxj"
	"github.com/gin-gonic/gin"
)

type EchoVo struct {
	Echo   string         `json:"echo"`
	Method string         `json:"method"`
	URL    string         `json:"url"`
	Header map[string]any `json:"headers"`
	Query  map[string]any `json:"query"`
	Body   any            `json:"body"`
}

type FileVo struct {
	Fieldname    string `json:"fieldname"`
	Originalname string `json:"originalname"`
	Mimetype     string `json:"mimetype"`
}

func Echo(c *gin.Context) {

	// 生成完整URL
	scheme := c.GetHeader("X-Forwarded-Proto")
	if scheme == "" {
		scheme = "http"
	}
	host := c.Request.Host
	url := fmt.Sprintf("%s://%s%s", scheme, host, c.Request.URL.String())

	header := make(map[string]any)
	for k, v := range c.Request.Header {
		header[k] = strings.Join(v, ", ")
	}

	query := make(map[string]any)
	for k, v := range c.Request.URL.Query() {
		if len(v) > 1 {
			query[k] = v
		} else {
			query[k] = v[0]
		}
	}

	// 解析请求体
	contentType := c.ContentType()
	fmt.Print("contentType: ", contentType, "\n")
	var body any
	if strings.Contains(contentType, "application/xml") {
		rawData, _ := c.GetRawData()
		body, _ = mxj.NewMapXml(rawData)
	} else if strings.Contains(contentType, "application/json") {
		rawData, _ := c.GetRawData()
		_ = json.Unmarshal(rawData, &body)
	} else if strings.Contains(contentType, "multipart/form-data") {
		c.Request.ParseMultipartForm(32 << 20)
		formData := make(map[string]any)
		type FileInfo struct {
			Filename    string
			Size        int64
			ContentType string
		}
		// 处理普通表单字段
		for k, v := range c.Request.MultipartForm.Value {
			if len(v) > 1 {
				formData[k] = v
			} else {
				formData[k] = v[0]
			}
		}
		// 处理文件字段
		if len(c.Request.MultipartForm.File) > 0 {
			files := make([]FileVo, 0)
			for fieldName, fhs := range c.Request.MultipartForm.File {
				for _, fh := range fhs {
					files = append(files, FileVo{
						Fieldname:    fieldName,
						Originalname: fh.Filename,
						Mimetype:     fh.Header.Get("Content-Type"),
					})
				}
			}
			formData["__file__"] = files
		}
		body = formData
	} else if strings.Contains(contentType, "application/x-www-form-urlencoded") {
		c.Request.ParseForm()
		formData := make(map[string]any)
		for k, v := range c.Request.PostForm {
			if len(v) > 1 {
				formData[k] = v
			} else {
				formData[k] = v[0]
			}
		}
		body = formData
	} else {
		rawData, _ := c.GetRawData()
		body = string(rawData)
	}

	c.JSON(200, EchoVo{
		Echo:   "gin",
		Method: c.Request.Method,
		URL:    url,
		Header: header,
		Query:  query,
		Body:   body,
	})
}
