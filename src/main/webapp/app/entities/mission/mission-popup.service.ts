import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Mission } from './mission.model';
import { MissionService } from './mission.service';

@Injectable()
export class MissionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private missionService: MissionService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.missionService.find(id).subscribe((mission) => {
                    if (mission.dateDebut) {
                        mission.dateDebut = {
                            year: mission.dateDebut.getFullYear(),
                            month: mission.dateDebut.getMonth() + 1,
                            day: mission.dateDebut.getDate()
                        };
                    }
                    if (mission.dateFin) {
                        mission.dateFin = {
                            year: mission.dateFin.getFullYear(),
                            month: mission.dateFin.getMonth() + 1,
                            day: mission.dateFin.getDate()
                        };
                    }
                    this.ngbModalRef = this.missionModalRef(component, mission);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.missionModalRef(component, new Mission());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    missionModalRef(component: Component, mission: Mission): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.mission = mission;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
