package server.persistance;

public interface IFactory {
	public IGameDAO createGameDAO();
	
	public IUserDAO createUserDAO();
}
