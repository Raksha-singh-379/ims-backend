import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ConsumptionDetailsService } from '../service/consumption-details.service';
import { IConsumptionDetails, ConsumptionDetails } from '../consumption-details.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IProductInventory } from 'app/entities/product-inventory/product-inventory.model';
import { ProductInventoryService } from 'app/entities/product-inventory/service/product-inventory.service';

import { ConsumptionDetailsUpdateComponent } from './consumption-details-update.component';

describe('ConsumptionDetails Management Update Component', () => {
  let comp: ConsumptionDetailsUpdateComponent;
  let fixture: ComponentFixture<ConsumptionDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let consumptionDetailsService: ConsumptionDetailsService;
  let securityUserService: SecurityUserService;
  let projectService: ProjectService;
  let productInventoryService: ProductInventoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ConsumptionDetailsUpdateComponent],
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
      .overrideTemplate(ConsumptionDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConsumptionDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    consumptionDetailsService = TestBed.inject(ConsumptionDetailsService);
    securityUserService = TestBed.inject(SecurityUserService);
    projectService = TestBed.inject(ProjectService);
    productInventoryService = TestBed.inject(ProductInventoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SecurityUser query and add missing value', () => {
      const consumptionDetails: IConsumptionDetails = { id: 456 };
      const securityUser: ISecurityUser = { id: 29947 };
      consumptionDetails.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 15421 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ consumptionDetails });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Project query and add missing value', () => {
      const consumptionDetails: IConsumptionDetails = { id: 456 };
      const project: IProject = { id: 85452 };
      consumptionDetails.project = project;

      const projectCollection: IProject[] = [{ id: 59051 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ consumptionDetails });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(projectCollection, ...additionalProjects);
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProductInventory query and add missing value', () => {
      const consumptionDetails: IConsumptionDetails = { id: 456 };
      const productInventory: IProductInventory = { id: 24248 };
      consumptionDetails.productInventory = productInventory;

      const productInventoryCollection: IProductInventory[] = [{ id: 64553 }];
      jest.spyOn(productInventoryService, 'query').mockReturnValue(of(new HttpResponse({ body: productInventoryCollection })));
      const additionalProductInventories = [productInventory];
      const expectedCollection: IProductInventory[] = [...additionalProductInventories, ...productInventoryCollection];
      jest.spyOn(productInventoryService, 'addProductInventoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ consumptionDetails });
      comp.ngOnInit();

      expect(productInventoryService.query).toHaveBeenCalled();
      expect(productInventoryService.addProductInventoryToCollectionIfMissing).toHaveBeenCalledWith(
        productInventoryCollection,
        ...additionalProductInventories
      );
      expect(comp.productInventoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const consumptionDetails: IConsumptionDetails = { id: 456 };
      const securityUser: ISecurityUser = { id: 39878 };
      consumptionDetails.securityUser = securityUser;
      const project: IProject = { id: 46264 };
      consumptionDetails.project = project;
      const productInventory: IProductInventory = { id: 53159 };
      consumptionDetails.productInventory = productInventory;

      activatedRoute.data = of({ consumptionDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(consumptionDetails));
      expect(comp.securityUsersSharedCollection).toContain(securityUser);
      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.productInventoriesSharedCollection).toContain(productInventory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ConsumptionDetails>>();
      const consumptionDetails = { id: 123 };
      jest.spyOn(consumptionDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consumptionDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: consumptionDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(consumptionDetailsService.update).toHaveBeenCalledWith(consumptionDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ConsumptionDetails>>();
      const consumptionDetails = new ConsumptionDetails();
      jest.spyOn(consumptionDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consumptionDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: consumptionDetails }));
      saveSubject.complete();

      // THEN
      expect(consumptionDetailsService.create).toHaveBeenCalledWith(consumptionDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ConsumptionDetails>>();
      const consumptionDetails = { id: 123 };
      jest.spyOn(consumptionDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consumptionDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(consumptionDetailsService.update).toHaveBeenCalledWith(consumptionDetails);
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

    describe('trackProductInventoryById', () => {
      it('Should return tracked ProductInventory primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProductInventoryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
