import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransferRecievedService } from '../service/transfer-recieved.service';
import { ITransferRecieved, TransferRecieved } from '../transfer-recieved.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { ITransfer } from 'app/entities/transfer/transfer.model';
import { TransferService } from 'app/entities/transfer/service/transfer.service';

import { TransferRecievedUpdateComponent } from './transfer-recieved-update.component';

describe('TransferRecieved Management Update Component', () => {
  let comp: TransferRecievedUpdateComponent;
  let fixture: ComponentFixture<TransferRecievedUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transferRecievedService: TransferRecievedService;
  let securityUserService: SecurityUserService;
  let transferService: TransferService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransferRecievedUpdateComponent],
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
      .overrideTemplate(TransferRecievedUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransferRecievedUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transferRecievedService = TestBed.inject(TransferRecievedService);
    securityUserService = TestBed.inject(SecurityUserService);
    transferService = TestBed.inject(TransferService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SecurityUser query and add missing value', () => {
      const transferRecieved: ITransferRecieved = { id: 456 };
      const securityUser: ISecurityUser = { id: 28769 };
      transferRecieved.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 40772 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transferRecieved });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Transfer query and add missing value', () => {
      const transferRecieved: ITransferRecieved = { id: 456 };
      const transfer: ITransfer = { id: 31395 };
      transferRecieved.transfer = transfer;

      const transferCollection: ITransfer[] = [{ id: 40118 }];
      jest.spyOn(transferService, 'query').mockReturnValue(of(new HttpResponse({ body: transferCollection })));
      const additionalTransfers = [transfer];
      const expectedCollection: ITransfer[] = [...additionalTransfers, ...transferCollection];
      jest.spyOn(transferService, 'addTransferToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transferRecieved });
      comp.ngOnInit();

      expect(transferService.query).toHaveBeenCalled();
      expect(transferService.addTransferToCollectionIfMissing).toHaveBeenCalledWith(transferCollection, ...additionalTransfers);
      expect(comp.transfersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const transferRecieved: ITransferRecieved = { id: 456 };
      const securityUser: ISecurityUser = { id: 32186 };
      transferRecieved.securityUser = securityUser;
      const transfer: ITransfer = { id: 27586 };
      transferRecieved.transfer = transfer;

      activatedRoute.data = of({ transferRecieved });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(transferRecieved));
      expect(comp.securityUsersSharedCollection).toContain(securityUser);
      expect(comp.transfersSharedCollection).toContain(transfer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransferRecieved>>();
      const transferRecieved = { id: 123 };
      jest.spyOn(transferRecievedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferRecieved });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transferRecieved }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(transferRecievedService.update).toHaveBeenCalledWith(transferRecieved);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransferRecieved>>();
      const transferRecieved = new TransferRecieved();
      jest.spyOn(transferRecievedService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferRecieved });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transferRecieved }));
      saveSubject.complete();

      // THEN
      expect(transferRecievedService.create).toHaveBeenCalledWith(transferRecieved);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransferRecieved>>();
      const transferRecieved = { id: 123 };
      jest.spyOn(transferRecievedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferRecieved });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transferRecievedService.update).toHaveBeenCalledWith(transferRecieved);
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

    describe('trackTransferById', () => {
      it('Should return tracked Transfer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTransferById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
