const Toast = Swal.mixin({
    toast: true,
    position: 'center-end',
    iconColor: 'white',
    customClass: {
        popup: 'colored-toast'
    },
    showConfirmButton: false,
    timer: 1500,
    timerProgressBar: true
})