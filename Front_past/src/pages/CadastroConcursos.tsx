
import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import { toast } from 'sonner';
import NavBar from '@/components/NavBar';
import { Input } from '@/components/ui/input';
import { Textarea } from '@/components/ui/textarea';
import { Button } from '@/components/ui/button';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form';

const formSchema = z.object({
  nome: z.string().min(3, { message: 'O nome deve ter pelo menos 3 caracteres' }),
  dataInicio: z.string().min(1, { message: 'A data de início é obrigatória' }),
  dataFim: z.string().min(1, { message: 'A data de término é obrigatória' }),
  categoria: z.string().min(1, { message: 'A categoria é obrigatória' }),
  nivelEnsino: z.string().min(1, { message: 'O nível de ensino é obrigatório' }),
  banca: z.string().min(1, { message: 'A banca é obrigatória' }),
  localizacao: z.string().min(1, { message: 'A localização é obrigatória' }),
  horario: z.string().min(1, { message: 'O horário é obrigatório' }),
  descricao: z.string().min(10, { message: 'A descrição deve ter pelo menos 10 caracteres' }),
  link: z.string().url({ message: 'Insira um URL válido' }),
  materiaisEstudo: z.string(),
});

type FormValues = z.infer<typeof formSchema>;

const CadastroConcursos: React.FC = () => {
  const form = useForm<FormValues>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      nome: '',
      dataInicio: '',
      dataFim: '',
      categoria: '',
      nivelEnsino: '',
      banca: '',
      localizacao: '',
      horario: '',
      descricao: '',
      link: '',
      materiaisEstudo: '',
    },
  });

  const onSubmit = (data: FormValues) => {
    console.log('Dados do concurso:', data);
    toast.success('Concurso cadastrado com sucesso!');
    form.reset();
  };

  return (
    <div className="min-h-screen flex flex-col">
      <NavBar />
      
      {/* Main Content with padding for the fixed navbar */}
      <main className="flex-1 container mx-auto px-4 pt-24 pb-16">
        <h1 className="text-2xl font-bold text-center text-vagacerta-blue mb-8">
          CADASTRO DE CONCURSOS
        </h1>
        
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6 max-w-4xl mx-auto">
            <FormField
              control={form.control}
              name="nome"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Nome</FormLabel>
                  <FormControl>
                    <Input placeholder="Nome do concurso" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <FormField
                control={form.control}
                name="dataInicio"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Data de Início</FormLabel>
                    <FormControl>
                      <Input type="date" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="dataFim"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Data de Término</FormLabel>
                    <FormControl>
                      <Input type="date" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>
            
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <FormField
                control={form.control}
                name="categoria"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Categoria</FormLabel>
                    <Select onValueChange={field.onChange} defaultValue={field.value}>
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Selecione uma categoria" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        <SelectItem value="federal">Federal</SelectItem>
                        <SelectItem value="estadual">Estadual</SelectItem>
                        <SelectItem value="municipal">Municipal</SelectItem>
                        <SelectItem value="outro">Outro</SelectItem>
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="nivelEnsino"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Nível de Ensino</FormLabel>
                    <Select onValueChange={field.onChange} defaultValue={field.value}>
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Selecione um nível" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        <SelectItem value="fundamental">Fundamental</SelectItem>
                        <SelectItem value="medio">Médio</SelectItem>
                        <SelectItem value="tecnico">Técnico</SelectItem>
                        <SelectItem value="superior">Superior</SelectItem>
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>
            
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <FormField
                control={form.control}
                name="banca"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Banca</FormLabel>
                    <FormControl>
                      <Input placeholder="Nome da banca examinadora" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="localizacao"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Localização</FormLabel>
                    <FormControl>
                      <Input placeholder="Cidade, Estado" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>
            
            <FormField
              control={form.control}
              name="horario"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Horário</FormLabel>
                  <FormControl>
                    <Input placeholder="Ex: 13:00 - 17:00" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            
            <FormField
              control={form.control}
              name="descricao"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Descrição</FormLabel>
                  <FormControl>
                    <Textarea 
                      placeholder="Descreva detalhes do concurso" 
                      className="min-h-[120px]" 
                      {...field} 
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            
            <FormField
              control={form.control}
              name="link"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Link</FormLabel>
                  <FormControl>
                    <Input placeholder="URL do edital ou site oficial" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            
            <FormField
              control={form.control}
              name="materiaisEstudo"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Materiais de Estudo</FormLabel>
                  <FormControl>
                    <Textarea 
                      placeholder="Links ou descrição de materiais de estudo recomendados" 
                      className="min-h-[100px]" 
                      {...field} 
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            
            <Button 
              type="submit" 
              className="w-full bg-orange-500 hover:bg-orange-600 text-white font-bold py-3 text-lg"
            >
              CADASTRAR
            </Button>
          </form>
        </Form>
      </main>
      
      {/* Footer */}
      <footer className="bg-vagacerta-blue text-white py-4 text-center">
        <p>© {new Date().getFullYear()} VagaCerta. Todos os direitos reservados.</p>
        <p className="text-sm mt-1">Contato: vagacerta@gmail.com</p>
      </footer>
    </div>
  );
};

export default CadastroConcursos;
