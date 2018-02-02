/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ServeurTestModule } from '../../../test.module';
import { LocalisationDialogComponent } from '../../../../../../main/webapp/app/entities/localisation/localisation-dialog.component';
import { LocalisationService } from '../../../../../../main/webapp/app/entities/localisation/localisation.service';
import { Localisation } from '../../../../../../main/webapp/app/entities/localisation/localisation.model';

describe('Component Tests', () => {

    describe('Localisation Management Dialog Component', () => {
        let comp: LocalisationDialogComponent;
        let fixture: ComponentFixture<LocalisationDialogComponent>;
        let service: LocalisationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServeurTestModule],
                declarations: [LocalisationDialogComponent],
                providers: [
                    LocalisationService
                ]
            })
            .overrideTemplate(LocalisationDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocalisationDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocalisationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Localisation('123');
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.localisation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'localisationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Localisation();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.localisation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'localisationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
