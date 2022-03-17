import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PurchaseQuotationDetailsDetailComponent } from './purchase-quotation-details-detail.component';

describe('PurchaseQuotationDetails Management Detail Component', () => {
  let comp: PurchaseQuotationDetailsDetailComponent;
  let fixture: ComponentFixture<PurchaseQuotationDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PurchaseQuotationDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ purchaseQuotationDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PurchaseQuotationDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PurchaseQuotationDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load purchaseQuotationDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.purchaseQuotationDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
