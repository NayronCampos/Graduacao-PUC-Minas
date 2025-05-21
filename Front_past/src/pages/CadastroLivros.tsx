
import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { Book, BookOpen, Pencil, Trash } from 'lucide-react';
import NavBar from '@/components/NavBar';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { Textarea } from '@/components/ui/textarea';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { useToast } from '@/hooks/use-toast';

interface Livro {
  id: number;
  titulo: string;
  descricao: string;
  editora: string;
  materia: string;
  link: string;
}

// Matérias disponíveis para seleção
const materias = [
  'Matemática',
  'Português',
  'Direito',
  'História',
  'Geografia',
  'Física',
  'Química',
  'Biologia',
  'Informática',
  'Outras'
];

const CadastroLivros = () => {
  const { toast } = useToast();
  const [livros, setLivros] = useState<Livro[]>([
    {
      id: 1,
      titulo: 'Algebrista Volume 1 Teoria e Exercícios de Fixação',
      descricao: 'Algebrista é todo aquele que possui grande habilidade em álgebra. Para passar nos concursos do colégio naval e atingir o aluno precisa se tornar um algebrista. Para iniciar a preparação para concursos é necessário, primeiramente, o aluno precisa ser um algebrista, dada a complexidade das questões e ao curto tempo para resolver cada questão.',
      editora: 'Ciência Moderna',
      materia: 'Matemática',
      link: 'https://exemplo.com/livro1'
    },
    {
      id: 2,
      titulo: 'Gramática completa para concursos e vestibulares',
      descricao: 'Um guia completo de gramática portuguesa focado em concursos públicos e vestibulares, contendo teoria e exercícios.',
      editora: 'Saraiva',
      materia: 'Português',
      link: 'https://exemplo.com/livro2'
    }
  ]);
  
  const [editingId, setEditingId] = useState<number | null>(null);
  
  const { register, handleSubmit, setValue, reset, formState: { errors } } = useForm<Livro>();

  const onSubmit = (data: Omit<Livro, 'id'>) => {
    if (editingId) {
      // Atualizar livro existente
      setLivros(livros.map(livro => 
        livro.id === editingId ? { ...data, id: editingId } : livro
      ));
      toast({
        title: "Livro atualizado",
        description: "O livro foi atualizado com sucesso!"
      });
      setEditingId(null);
    } else {
      // Adicionar novo livro
      const newBook = {
        ...data,
        id: livros.length > 0 ? Math.max(...livros.map(l => l.id)) + 1 : 1
      };
      setLivros([...livros, newBook]);
      toast({
        title: "Livro cadastrado",
        description: "O livro foi cadastrado com sucesso!"
      });
    }
    reset();
  };

  const handleEdit = (livro: Livro) => {
    setValue('titulo', livro.titulo);
    setValue('descricao', livro.descricao);
    setValue('editora', livro.editora);
    setValue('materia', livro.materia);
    setValue('link', livro.link);
    setEditingId(livro.id);
    
    // Scroll to form
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const handleDelete = (id: number) => {
    setLivros(livros.filter(livro => livro.id !== id));
    if (editingId === id) {
      reset();
      setEditingId(null);
    }
    
    toast({
      title: "Livro removido",
      description: "O livro foi removido com sucesso!"
    });
  };

  const cancelEdit = () => {
    reset();
    setEditingId(null);
  };

  return (
    <div className="min-h-screen bg-gray-100">
      <NavBar />
      <main className="container mx-auto px-4 pt-24 pb-24">
        <div className="bg-white rounded-lg shadow-md p-6 mb-8">
          <h1 className="text-3xl font-bold text-center text-vagacerta-blue mb-8">
            {editingId ? "Editar Livro" : "Cadastrar Livros"}
          </h1>
          
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 max-w-3xl mx-auto">
            <div>
              <label htmlFor="titulo" className="block text-sm font-medium text-gray-700 mb-1">
                Título do Livro
              </label>
              <Input 
                id="titulo"
                placeholder="Digite o título do livro"
                {...register('titulo', { required: true })}
                className={errors.titulo ? "border-red-500" : ""}
              />
              {errors.titulo && <span className="text-red-500 text-sm">Título é obrigatório</span>}
            </div>
            
            <div>
              <label htmlFor="descricao" className="block text-sm font-medium text-gray-700 mb-1">
                Descrição do Livro
              </label>
              <Textarea 
                id="descricao"
                placeholder="Digite a descrição do livro"
                rows={4}
                {...register('descricao', { required: true })}
                className={errors.descricao ? "border-red-500" : ""}
              />
              {errors.descricao && <span className="text-red-500 text-sm">Descrição é obrigatória</span>}
            </div>
            
            <div>
              <label htmlFor="editora" className="block text-sm font-medium text-gray-700 mb-1">
                Editora do Livro
              </label>
              <Input 
                id="editora"
                placeholder="Digite a editora do livro"
                {...register('editora', { required: true })}
                className={errors.editora ? "border-red-500" : ""}
              />
              {errors.editora && <span className="text-red-500 text-sm">Editora é obrigatória</span>}
            </div>
            
            <div>
              <label htmlFor="materia" className="block text-sm font-medium text-gray-700 mb-1">
                Matéria do Livro
              </label>
              <Select 
                onValueChange={(value) => setValue('materia', value)} 
                defaultValue={editingId ? livros.find(l => l.id === editingId)?.materia : ""}
              >
                <SelectTrigger>
                  <SelectValue placeholder="Selecione uma matéria" />
                </SelectTrigger>
                <SelectContent>
                  {materias.map((materia) => (
                    <SelectItem key={materia} value={materia}>
                      {materia}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
              {errors.materia && <span className="text-red-500 text-sm">Matéria é obrigatória</span>}
            </div>
            
            <div>
              <label htmlFor="link" className="block text-sm font-medium text-gray-700 mb-1">
                Link do Livro
              </label>
              <Input 
                id="link"
                placeholder="Digite o link para o livro"
                {...register('link', { required: true })}
                className={errors.link ? "border-red-500" : ""}
              />
              {errors.link && <span className="text-red-500 text-sm">Link é obrigatório</span>}
            </div>
            
            <div className="flex gap-4 justify-end pt-4">
              {editingId && (
                <Button 
                  type="button" 
                  variant="outline" 
                  onClick={cancelEdit}
                >
                  Cancelar
                </Button>
              )}
              <Button 
                type="submit" 
                className="bg-green-500 hover:bg-green-600 text-white"
              >
                {editingId ? "Atualizar" : "Cadastrar"}
              </Button>
            </div>
          </form>
        </div>
        
        {/* Lista de Livros cadastrados */}
        <div className="bg-white rounded-lg shadow-md p-6 mb-8">
          <h2 className="text-2xl font-bold text-center text-vagacerta-blue mb-6">
            Livros Cadastrados
          </h2>
          
          <div className="space-y-6">
            {livros.length > 0 ? (
              livros.map(livro => (
                <Card key={livro.id} className="overflow-hidden">
                  <CardContent className="p-6">
                    <div className="flex justify-between items-start">
                      <div className="flex-1">
                        <h3 className="text-xl font-bold text-vagacerta-blue mb-2">{livro.titulo}</h3>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
                          <div>
                            <p className="text-sm"><span className="font-semibold">Matéria:</span> {livro.materia}</p>
                            <p className="text-sm"><span className="font-semibold">Editora:</span> {livro.editora}</p>
                          </div>
                        </div>
                        <p className="text-sm mb-4"><span className="font-semibold">Descrição:</span> {livro.descricao}</p>
                        <div>
                          <a 
                            href={livro.link} 
                            target="_blank" 
                            rel="noopener noreferrer" 
                            className="text-blue-600 hover:text-blue-800 text-sm"
                          >
                            Clique aqui
                          </a>
                        </div>
                      </div>
                      <div className="flex flex-col space-y-2 ml-4">
                        <Button 
                          variant="ghost" 
                          size="icon" 
                          onClick={() => handleEdit(livro)}
                          className="text-amber-500 hover:text-amber-600 hover:bg-amber-50"
                        >
                          <Pencil size={16} />
                        </Button>
                        <Button 
                          variant="ghost" 
                          size="icon" 
                          onClick={() => handleDelete(livro.id)}
                          className="text-red-500 hover:text-red-600 hover:bg-red-50"
                        >
                          <Trash size={16} />
                        </Button>
                      </div>
                    </div>
                  </CardContent>
                </Card>
              ))
            ) : (
              <div className="text-center py-12">
                <BookOpen className="mx-auto h-12 w-12 text-gray-400 mb-4" />
                <h3 className="text-lg font-medium text-gray-900">Nenhum livro cadastrado</h3>
                <p className="mt-1 text-sm text-gray-500">Comece adicionando um novo livro.</p>
              </div>
            )}
          </div>
        </div>
      </main>

      <footer className="bg-vagacerta-blue text-white py-4 text-center">
        <p>© {new Date().getFullYear()} VagaCerta. Todos os direitos reservados.</p>
        <p className="text-sm mt-1">Contato: vagacerta@gmail.com</p>
      </footer>
    </div>
  );
};

export default CadastroLivros;
