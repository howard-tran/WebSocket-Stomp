package mongodbservice

import (
	"GoBackend/utility"

	"gopkg.in/mgo.v2"
)

type DBService interface {
	CloseDBService()
	GetSession() *mgo.Session
	//InsertCollection
}

type dbService struct {
	session *mgo.Session
}

func NewDBService() (*dbService, error) {
	configconnectDB := utility.GetConfigServerbyKey(utility.Database).(utility.DatabaseStruct)
	sec, err := mgo.Dial(configconnectDB.Stringconnection)
	if err != nil {
		return &dbService{session: nil}, err
	}
	return &dbService{session: sec}, nil
}

func (s *dbService) GetSession() *mgo.Session {
	return s.session
}

func (s *dbService) CloseDBService() {
	s.session.Close()
}
