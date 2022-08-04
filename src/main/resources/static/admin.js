showUsersTable()

function showUsersTable() {
    $('#users-table').empty()

    fetch('/api/admin').then(response => {
        response.json().then((userList) => {
            userList.forEach(user => {
                const row = document.createElement('tr')
                row.id = "row-" + user.id

                const idTh = document.createElement('th')
                idTh.setAttribute('font-weight', 'bold')
                idTh.textContent = user.id

                const firstNameTd = document.createElement('td')
                firstNameTd.textContent = user.firstName

                const lastNameTd = document.createElement('td')
                lastNameTd.textContent = user.lastName

                const ageTd = document.createElement('td')
                ageTd.textContent = user.age

                const emailTd = document.createElement('td')

                emailTd.textContent = user.email

                const rolesTd = document.createElement('td')
                rolesTd.style.whiteSpace = 'pre-line'
                let setRoles = ''
                user.roles.forEach(role => {
                    setRoles += role.role.replace('ROLE_', '') + '\n'
                })
                rolesTd.textContent = setRoles

                const editButtonTd = document.createElement('td')

                const editTd = document.createElement('button')
                editTd.className = 'btn text-light'
                editTd.setAttribute('type', 'button')
                editTd.setAttribute('data-bs-toggle', 'modal')
                editTd.setAttribute('data-bs-target', '#edit-model')
                editButtonTd.onclick = () => {
                    $("#update-id-input").val(user.id)
                    $("#update-first-name-input").val(user.firstName)
                    $("#update-last-name-input").val(user.lastName)
                    $("#update-age-input").val(user.age)
                    $("#update-email-input").val(user.email)
                    $("#update-password-input").val('')
                }
                editTd.style.backgroundColor = '#17A2B8'
                editTd.textContent = 'Edit'
                editButtonTd.appendChild(editTd)

                const deleteButtonTd = document.createElement('td')

                const deleteTd = document.createElement('button')
                deleteTd.className = 'btn btn-danger'
                deleteTd.setAttribute('type', 'button')
                deleteTd.setAttribute('data-bs-toggle', 'modal')
                deleteTd.setAttribute('data-bs-target', '#delete-modal')
                deleteButtonTd.onclick = () => {
                    $("#delete-id-input").val(user.id)
                    $("#delete-first-name-input").val(user.firstName)
                    $("#delete-last-name-input").val(user.lastName)
                    $("#delete-age-input").val(user.age)
                    $("#delete-email-input").val(user.email)
                }
                deleteTd.textContent = 'Delete'
                deleteButtonTd.appendChild(deleteTd)

                row.appendChild(idTh)
                row.appendChild(firstNameTd)
                row.appendChild(lastNameTd)
                row.appendChild(ageTd)
                row.appendChild(emailTd)
                row.appendChild(rolesTd)
                row.appendChild(editButtonTd)
                row.appendChild(deleteButtonTd)


                document.querySelector('#users-table').appendChild(row)
            })
        })
    })
}

function editUser() {
    const user = {
        id: Number($('#update-id-input').val()),
        firstName: $('#update-first-name-input').val(),
        lastName: $('#update-last-name-input').val(),
        age: Number($('#update-age-input').val()),
        email: $('#update-email-input').val(),
        password: $('#update-password-input').val(),
        roles: $('#update-role-input').val(),
    }

    if (user.firstName !== '' && user.lastName !== '' && user.age !== 0 && user.email !== '') {
        $.ajax({
            url: '/api/edit/' + document.querySelector("#update-id-input").value,
            type: 'PATCH',
            contentType: 'application/merge-patch+json',
            data: JSON.stringify(user),
            success: () => {
                const row = $('#row-' + user.id)[0]

                row.cells[1].textContent = user.firstName
                row.cells[2].textContent = user.lastName
                row.cells[3].textContent = user.age.toString()
                row.cells[4].textContent = user.email
                let setRoles2 = ''
                user.roles.forEach(role => {
                    setRoles2 += role + '\n'
                })
                row.cells[5].textContent = setRoles2

                $('#update-close').click()
            }
        });
    } else {
        alert('Enter all!')
    }
}

function addUser() {
    const newUser = {
        firstName: $('#first-name-input').val(),
        lastName: $('#last-name-input').val(),
        age: Number($('#age-input').val()),
        email: $('#email-input').val(),
        password: $('#password-input').val(),
        roles: $('#role-input').val()
    }


    if (newUser.firstName !== '' && newUser.lastName !== '' && newUser.age !== 0 && newUser.email !== '' && newUser.password !== '') {
        $.ajax({
            url: '/api/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(newUser),
            success: () => {
                showUsersTable()
                document.querySelector('#nav-table-tab').click()

                $('#new-user-form')[0].reset()
            }
        });
    } else {
        alert('Enter all!')
    }
}

function deleteUser() {
    $.ajax({
        url: '/api/delete/' + $('#delete-id-input').val(),
        type: 'DELETE',
        success: () => {
            $('#row-' + $('#delete-id-input').val())[0].remove()
            $('#delete-close').click()
        }
    });
}