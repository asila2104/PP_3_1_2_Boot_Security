userPage()

function userPage() {
    fetch('/api/user').then(response => {
        response.json().then(user => {
            $("#id").text(user.id)
            $("#firstName").text(user.firstName)
            $("#lastName").text(user.lastName)
            $("#age").text(user.age)
            $("#email").text(user.email)
            $("#email-header").text(user.email)

            let setRoles = ''
            user.roles.forEach(role => {
                setRoles += role.role.replace('ROLE_', '') + '\n'
            })
            $("#roles").text(setRoles)
            $("#roles-header").text(setRoles)
        })
    })
}

