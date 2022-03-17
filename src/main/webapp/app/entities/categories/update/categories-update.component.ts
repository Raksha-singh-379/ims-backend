import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICategories, Categories } from '../categories.model';
import { CategoriesService } from '../service/categories.service';

@Component({
  selector: 'jhi-categories-update',
  templateUrl: './categories-update.component.html',
})
export class CategoriesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    categoryName: [],
    categoryDescription: [],
    freeField1: [],
    lastModified: [],
    lastModifiedBy: [],
    isDeleted: [],
    isActive: [],
  });

  constructor(protected categoriesService: CategoriesService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categories }) => {
      this.updateForm(categories);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categories = this.createFromForm();
    if (categories.id !== undefined) {
      this.subscribeToSaveResponse(this.categoriesService.update(categories));
    } else {
      this.subscribeToSaveResponse(this.categoriesService.create(categories));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategories>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(categories: ICategories): void {
    this.editForm.patchValue({
      id: categories.id,
      categoryName: categories.categoryName,
      categoryDescription: categories.categoryDescription,
      freeField1: categories.freeField1,
      lastModified: categories.lastModified,
      lastModifiedBy: categories.lastModifiedBy,
      isDeleted: categories.isDeleted,
      isActive: categories.isActive,
    });
  }

  protected createFromForm(): ICategories {
    return {
      ...new Categories(),
      id: this.editForm.get(['id'])!.value,
      categoryName: this.editForm.get(['categoryName'])!.value,
      categoryDescription: this.editForm.get(['categoryDescription'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
    };
  }
}
