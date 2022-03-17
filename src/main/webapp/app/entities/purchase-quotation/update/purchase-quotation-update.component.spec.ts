import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PurchaseQuotationService } from '../service/purchase-quotation.service';
import { IPurchaseQuotation, PurchaseQuotation } from '../purchase-quotation.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IClientDetails } from 'app/entities/client-details/client-details.model';
import { ClientDetailsService } from 'app/entities/client-details/service/client-details.service';

import { PurchaseQuotationUpdateComponent } from './purchase-quotation-update.component';

describe('PurchaseQuotation Management Update Component', () => {
  let comp: PurchaseQuotationUpdateComponent;
  let fixture: ComponentFixture<PurchaseQuotationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let purchaseQuotationService: PurchaseQuotationService;
  let securityUserService: SecurityUserService;
  let projectService: ProjectService;
  let clientDetailsService: ClientDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PurchaseQuotationUpdateComponent],
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
      .overrideTemplate(PurchaseQuotationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PurchaseQuotationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    purchaseQuotationService = TestBed.inject(PurchaseQuotationService);
    securityUserService = TestBed.inject(SecurityUserService);
    projectService = TestBed.inject(ProjectService);
    clientDetailsService = TestBed.inject(ClientDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SecurityUser query and add missing value', () => {
      const purchaseQuotation: IPurchaseQuotation = { id: 456 };
      const securityUser: ISecurityUser = { id: 16779 };
      purchaseQuotation.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 6187 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ purchaseQuotation });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Project query and add missing value', () => {
      const purchaseQuotation: IPurchaseQuotation = { id: 456 };
      const project: IProject = { id: 77203 };
      purchaseQuotation.project = project;

      const projectCollection: IProject[] = [{ id: 75122 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ purchaseQuotation });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(projectCollection, ...additionalProjects);
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ClientDetails query and add missing value', () => {
      const purchaseQuotation: IPurchaseQuotation = { id: 456 };
      const clientDetails: IClientDetails = { id: 25151 };
      purchaseQuotation.clientDetails = clientDetails;

      const clientDetailsCollection: IClientDetails[] = [{ id: 35314 }];
      jest.spyOn(clientDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: clientDetailsCollection })));
      const additionalClientDetails = [clientDetails];
      const expectedCollection: IClientDetails[] = [...additionalClientDetails, ...clientDetailsCollection];
      jest.spyOn(clientDetailsService, 'addClientDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ purchaseQuotation });
      comp.ngOnInit();

      expect(clientDetailsService.query).toHaveBeenCalled();
      expect(clientDetailsService.addClientDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        clientDetailsCollection,
        ...additionalClientDetails
      );
      expect(comp.clientDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const purchaseQuotation: IPurchaseQuotation = { id: 456 };
      const securityUser: ISecurityUser = { id: 32982 };
      purchaseQuotation.securityUser = securityUser;
      const project: IProject = { id: 93214 };
      purchaseQuotation.project = project;
      const clientDetails: IClientDetails = { id: 33642 };
      purchaseQuotation.clientDetails = clientDetails;

      activatedRoute.data = of({ purchaseQuotation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(purchaseQuotation));
      expect(comp.securityUsersSharedCollection).toContain(securityUser);
      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.clientDetailsSharedCollection).toContain(clientDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PurchaseQuotation>>();
      const purchaseQuotation = { id: 123 };
      jest.spyOn(purchaseQuotationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ purchaseQuotation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: purchaseQuotation }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(purchaseQuotationService.update).toHaveBeenCalledWith(purchaseQuotation);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PurchaseQuotation>>();
      const purchaseQuotation = new PurchaseQuotation();
      jest.spyOn(purchaseQuotationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ purchaseQuotation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: purchaseQuotation }));
      saveSubject.complete();

      // THEN
      expect(purchaseQuotationService.create).toHaveBeenCalledWith(purchaseQuotation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PurchaseQuotation>>();
      const purchaseQuotation = { id: 123 };
      jest.spyOn(purchaseQuotationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ purchaseQuotation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(purchaseQuotationService.update).toHaveBeenCalledWith(purchaseQuotation);
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

    describe('trackProjectById', () => {
      it('Should return tracked Project primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProjectById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackClientDetailsById', () => {
      it('Should return tracked ClientDetails primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackClientDetailsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
