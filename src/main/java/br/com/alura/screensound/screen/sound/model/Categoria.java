package br.com.alura.screensound.screen.sound.model;

public enum Categoria {
    SOLO ("solo"),
    DUPLA("dupla"),
    BANDA("banda");

    private String categoriaUsuario;
    Categoria(String categoriaUsuario){
        this.categoriaUsuario = categoriaUsuario;
    }

    public static Categoria fromUsuario(String text){
        for(Categoria categoria : Categoria.values()){
            if(categoria.categoriaUsuario.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada nesta descrição: " + text);
    }
}
