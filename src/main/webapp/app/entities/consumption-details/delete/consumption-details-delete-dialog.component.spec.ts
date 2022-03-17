jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ConsumptionDetailsService } from '../service/consumption-details.service';

import { ConsumptionDetailsDeleteDialogComponent } from './consumption-details-delete-dialog.component';

describe('ConsumptionDetails Management Delete Component', () => {
  let comp: ConsumptionDetailsDeleteDialogComponent;
  let fixture: ComponentFixture<ConsumptionDetailsDeleteDialogComponent>;
  let service: ConsumptionDetailsService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ConsumptionDetailsDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(ConsumptionDetailsDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ConsumptionDetailsDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ConsumptionDetailsService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
