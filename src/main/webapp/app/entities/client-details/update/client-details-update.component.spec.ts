import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ClientDetailsService } from '../service/client-details.service';
import { IClientDetails, ClientDetails } from '../client-details.model';

import { ClientDetailsUpdateComponent } from './client-details-update.component';

describe('ClientDetails Management Update Component', () => {
  let comp: ClientDetailsUpdateComponent;
  let fixture: ComponentFixture<ClientDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let clientDetailsService: ClientDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ClientDetailsUpdateComponent],
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
      .overrideTemplate(ClientDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClientDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    clientDetailsService = TestBed.inject(ClientDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const clientDetails: IClientDetails = { id: 456 };

      activatedRoute.data = of({ clientDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(clientDetails));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ClientDetails>>();
      const clientDetails = { id: 123 };
      jest.spyOn(clientDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clientDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: clientDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(clientDetailsService.update).toHaveBeenCalledWith(clientDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ClientDetails>>();
      const clientDetails = new ClientDetails();
      jest.spyOn(clientDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clientDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: clientDetails }));
      saveSubject.complete();

      // THEN
      expect(clientDetailsService.create).toHaveBeenCalledWith(clientDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ClientDetails>>();
      const clientDetails = { id: 123 };
      jest.spyOn(clientDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clientDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(clientDetailsService.update).toHaveBeenCalledWith(clientDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
