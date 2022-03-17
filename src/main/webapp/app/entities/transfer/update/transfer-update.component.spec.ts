import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransferService } from '../service/transfer.service';
import { ITransfer, Transfer } from '../transfer.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { IWareHouse } from 'app/entities/ware-house/ware-house.model';
import { WareHouseService } from 'app/entities/ware-house/service/ware-house.service';

import { TransferUpdateComponent } from './transfer-update.component';

describe('Transfer Management Update Component', () => {
  let comp: TransferUpdateComponent;
  let fixture: ComponentFixture<TransferUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transferService: TransferService;
  let securityUserService: SecurityUserService;
  let wareHouseService: WareHouseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransferUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TransferUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransferUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transferService = TestBed.inject(TransferService);
    securityUserService = TestBed.inject(SecurityUserService);
    wareHouseService = TestBed.inject(WareHouseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SecurityUser query and add missing value', () => {
      const transfer: ITransfer = { id: 456 };
      const securityUser: ISecurityUser = { id: 79750 };
      transfer.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 7120 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transfer });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call WareHouse query and add missing value', () => {
      const transfer: ITransfer = { id: 456 };
      const wareHouse: IWareHouse = { id: 38912 };
      transfer.wareHouse = wareHouse;

      const wareHouseCollection: IWareHouse[] = [{ id: 18349 }];
      jest.spyOn(wareHouseService, 'query').mockReturnValue(of(new HttpResponse({ body: wareHouseCollection })));
      const additionalWareHouses = [wareHouse];
      const expectedCollection: IWareHouse[] = [...additionalWareHouses, ...wareHouseCollection];
      jest.spyOn(wareHouseService, 'addWareHouseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transfer });
      comp.ngOnInit();

      expect(wareHouseService.query).toHaveBeenCalled();
      expect(wareHouseService.addWareHouseToCollectionIfMissing).toHaveBeenCalledWith(wareHouseCollection, ...additionalWareHouses);
      expect(comp.wareHousesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const transfer: ITransfer = { id: 456 };
      const securityUser: ISecurityUser = { id: 29133 };
      transfer.securityUser = securityUser;
      const wareHouse: IWareHouse = { id: 9758 };
      transfer.wareHouse = wareHouse;

      activatedRoute.data = of({ transfer });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(transfer));
      expect(comp.securityUsersSharedCollection).toContain(securityUser);
      expect(comp.wareHousesSharedCollection).toContain(wareHouse);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Transfer>>();
      const transfer = { id: 123 };
      jest.spyOn(transferService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transfer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transfer }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(transferService.update).toHaveBeenCalledWith(transfer);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Transfer>>();
      const transfer = new Transfer();
      jest.spyOn(transferService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transfer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transfer }));
      saveSubject.complete();

      // THEN
      expect(transferService.create).toHaveBeenCalledWith(transfer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Transfer>>();
      const transfer = { id: 123 };
      jest.spyOn(transferService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transfer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transferService.update).toHaveBeenCalledWith(transfer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSecurityUserById', () => {
      it('Should return tracked SecurityUser primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSecurityUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackWareHouseById', () => {
      it('Should return tracked WareHouse primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackWareHouseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
