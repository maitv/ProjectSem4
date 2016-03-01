/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Zito
 */
@Stateless
public class BookingServiceFacade extends AbstractFacade<BookingService> {

    @PersistenceContext(unitName = "ProjectSem4-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookingServiceFacade() {
        super(BookingService.class);
    }
    
}
