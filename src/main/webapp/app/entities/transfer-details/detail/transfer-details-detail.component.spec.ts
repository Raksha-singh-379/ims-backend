import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TransferDetailsDetailComponent } from './transfer-details-detail.component';

describe('TransferDetails Management Detail Component', () => {
  let comp: TransferDetailsDetailComponent;
  let fixture: ComponentFixture<TransferDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransferDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ transferDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TransferDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TransferDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load transferDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.transferDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
