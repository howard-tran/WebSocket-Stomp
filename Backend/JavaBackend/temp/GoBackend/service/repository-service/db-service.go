package mongodbservice

import (
	"GoBackend/utility"

	"gopkg.in/mgo.v2"
)

type DBService interface {
	CloseDBService()
	GetSession() *mgo.Session
}

type dbService struct {
	session *mgo.Session
}

var instance dbService

func NewDBService() (*dbService, error) {
	if instance == (dbService{}) {
		configconnectDB := utility.GetConfigServerbyKey(utility.Database).(utility.DatabaseStruct)
		sec, err := mgo.Dial(configconnectDB.Stringconnection)
		if err != nil {
			return &dbService{session: nil}, err
		}
		instance.session = sec
		//return &dbService{session: sec}, nil
	}
	return &instance, nil
}

func (s *dbService) GetSession() *mgo.Session {
	return s.session
}

func (s *dbService) CloseDBService() {
	s.session.Close()
}

func (s *dbService) GetDatabase(name string) *mgo.Database {
	return s.session.DB(name)
}
