package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

    // Forma de não expor a implementação, e deixar somente a interface --- Injeção de Dependências
    public static SellerDao createSellerDao(){
        new SellerDaoJDBC();
    }

}
