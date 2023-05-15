package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

    /*
    Forma de não expor a implementação, e deixar somente a interface
    --------------------------------------------------------------------------------------------------------------------
    DaoFactory pode ser considerado um "facilitador” para criar uma instância de algum objeto (conexão de banco),
    pois através de um método, você já recebe essa instância aberta e devidamente configurada.
    Isso tudo se faz dentro do Factory, ou seja, você poupa ficar escrevendo código toda vez que precisa abrir uma conexão.
    */

    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    };
}
