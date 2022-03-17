import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConsumptionDetailsDetailComponent } from './consumption-details-detail.component';

describe('ConsumptionDetails Management Detail Component', () => {
  let comp: ConsumptionDetailsDetailComponent;
  let fixture: ComponentFixture<ConsumptionDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConsumptionDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ consumptionDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ConsumptionDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ConsumptionDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load consumptionDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.consumptionDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
