import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransferDetailsApprovalsService } from '../service/transfer-details-approvals.service';
import { ITransferDetailsApprovals, TransferDetailsApprovals } from '../transfer-details-approvals.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { ITransfer } from 'app/entities/transfer/transfer.model';
import { TransferService } from 'app/entities/transfer/service/transfer.service';

import { TransferDetailsApprovalsUpdateComponent } from './transfer-details-approvals-update.component';

describe('TransferDetailsApprovals Management Update Component', () => {
  let comp: TransferDetailsApprovalsUpdateComponent;
  let fixture: ComponentFixture<TransferDetailsApprovalsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transferDetailsApprovalsService: TransferDetailsApprovalsService;
  let securityUserService: SecurityUserService;
  let transferService: TransferService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransferDetailsApprovalsUpdateComponent],
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
      .overrideTemplate(TransferDetailsApprovalsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransferDetailsApprovalsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transferDetailsApprovalsService = TestBed.inject(TransferDetailsApprovalsService);
    securityUserService = TestBed.inject(SecurityUserService);
    transferService = TestBed.inject(TransferService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SecurityUser query and add missing value', () => {
      const transferDetailsApprovals: ITransferDetailsApprovals = { id: 456 };
      const securityUser: ISecurityUser = { id: 81600 };
      transferDetailsApprovals.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 25815 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transferDetailsApprovals });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Transfer query and add missing value', () => {
      const transferDetailsApprovals: ITransferDetailsApprovals = { id: 456 };
      const transfer: ITransfer = { id: 55099 };
      transferDetailsApprovals.transfer = transfer;

      const transferCollection: ITransfer[] = [{ id: 41581 }];
      jest.spyOn(transferService, 'query').mockReturnValue(of(new HttpResponse({ body: transferCollection })));
      const additionalTransfers = [transfer];
      const expectedCollection: ITransfer[] = [...additionalTransfers, ...transferCollection];
      jest.spyOn(transferService, 'addTransferToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transferDetailsApprovals });
      comp.ngOnInit();

      expect(transferService.query).toHaveBeenCalled();
      expect(transferService.addTransferToCollectionIfMissing).toHaveBeenCalledWith(transferCollection, ...additionalTransfers);
      expect(comp.transfersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const transferDetailsApprovals: ITransferDetailsApprovals = { id: 456 };
      const securityUser: ISecurityUser = { id: 49005 };
      transferDetailsApprovals.securityUser = securityUser;
      const transfer: ITransfer = { id: 20867 };
      transferDetailsApprovals.transfer = transfer;

      activatedRoute.data = of({ transferDetailsApprovals });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(transferDetailsApprovals));
      expect(comp.securityUsersSharedCollection).toContain(securityUser);
      expect(comp.transfersSharedCollection).toContain(transfer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransferDetailsApprovals>>();
      const transferDetailsApprovals = { id: 123 };
      jest.spyOn(transferDetailsApprovalsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferDetailsApprovals });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transferDetailsApprovals }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(transferDetailsApprovalsService.update).toHaveBeenCalledWith(transferDetailsApprovals);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransferDetailsApprovals>>();
      const transferDetailsApprovals = new TransferDetailsApprovals();
      jest.spyOn(transferDetailsApprovalsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferDetailsApprovals });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transferDetailsApprovals }));
      saveSubject.complete();

      // THEN
      expect(transferDetailsApprovalsService.create).toHaveBeenCalledWith(transferDetailsApprovals);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransferDetailsApprovals>>();
      const transferDetailsApprovals = { id: 123 };
      jest.spyOn(transferDetailsApprovalsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transferDetailsApprovals });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transferDetailsApprovalsService.update).toHaveBeenCalledWith(transferDetailsApprovals);
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
