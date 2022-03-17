import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductTransactionDetailComponent } from './product-transaction-detail.component';

describe('ProductTransaction Management Detail Component', () => {
  let comp: ProductTransactionDetailComponent;
  let fixture: ComponentFixture<ProductTransactionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductTransactionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ productTransaction: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProductTransactionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductTransactionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load productTransaction on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.productTransaction).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
